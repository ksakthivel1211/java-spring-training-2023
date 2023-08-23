package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.dao.EmployeeRepository;
import com.sakthivel.spring.boot.crudDemo.dao.RoomRepository;
import com.sakthivel.spring.boot.crudDemo.dao.TeamsRepository;
import com.sakthivel.spring.boot.crudDemo.dao.TimeSlotRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.respose.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.respose.RoomResponse;
import com.sakthivel.spring.boot.crudDemo.respose.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.respose.TimeSlotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {


    private TimeSlot timeSlot;
    private TeamsRepository teamsRepository;
    private EmployeeRepository employeeRepository;
    private TimeSlotRepository timeSlotRepository;
    private RoomRepository roomRepository;

    @Autowired
    public TimeSlotService(EmployeeRepository theEmployee, RoomRepository theRoomRepository,TimeSlotRepository theTimeSlotRepository,TeamsRepository theTeamRepository){
        employeeRepository = theEmployee;
        roomRepository = theRoomRepository;
        timeSlotRepository=theTimeSlotRepository;
        teamsRepository=theTeamRepository;
    }

    /**
     * getAllTeams method returns all the existing teams
     * @return - returns List of all teams object
     */
    public List<TimeSlotResponse> getAllTimeSlots()
    {
        List<TimeSlot> theTimeSlot= timeSlotRepository.findAll();
        List<TimeSlotResponse> timeSlotResponses = theTimeSlot.stream().map(timeSlot -> {
            return getTimeSlotResponse(timeSlot);
        }).collect(Collectors.toList());

        return timeSlotResponses;
    }

    /**
     * getTeamById method returns team details (Team object) whose id is given
     * @param timeSlotId - team id with type int passed as parameter whose team details should be found
     * @return returns the employee Object of the given Employee id
     */
    public TimeSlotResponse getTimeSlotWithId(int timeSlotId)
    {
        Optional<TimeSlot> result = timeSlotRepository.findById(timeSlotId);

        TimeSlot theTimeSlot = null;
        if(result.isPresent())
        {
            theTimeSlot=result.get();

            return getTimeSlotResponse(theTimeSlot);
        }
        else {
            throw new RuntimeException("Time Slot id not found :"+timeSlotId);
        }
    }

    /**
     * CollaborationBooking saves the given timeSlot to the db
     * @param theTimeSlot -time slot of type TimeSlot
     * @param employeeId - employeeId of type integer
     * @param employeeIds - employeeIds of type List of integers
     * @param roomName - room name of type String
     * @return - Returns a string confirmation message of the save operation
     */
    public String collaborationBooking(TimeSlot theTimeSlot, int employeeId, List<Integer> employeeIds, String roomName)
    {

        int teamSize= employeeIds.size();
        if(roomIsAvailableForTime(theTimeSlot.getDate(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),roomName)) {
            if(capacityCheck(teamSize,roomName))
            {
            theTimeSlot.setTimeSlotId(0);
            Employee theEmployee = employeeRepository.findById(employeeId).orElse(null);
            theTimeSlot.setEmployee(theEmployee);

            Room theRoom = roomRepository.findByRoomName(roomName);
            theTimeSlot.setRooms(Collections.singletonList(theRoom));

            TimeSlot dbTimeSlot = timeSlotRepository.save(theTimeSlot);
            return dbTimeSlot.toString();
        }
            else
            {
                return "team count exceeds room capacity";
            }
        }
        else {
            return "booking failed : Room is not available at that time";
        }
    }

    /**
     * TeamBooking saves the given timeSlot to the db
     * @param theTimeSlot -time slot of type TimeSlot
     * @param employeeId - employeeId of type integer
     * @param roomName - room name of type String
     * @return - Returns a string confirmation message of the save operation
     */
    public String TeamBooking(TimeSlot theTimeSlot, int employeeId, int teamId, String roomName)
    {
        Teams theTeams = teamsRepository.findById(teamId).orElse(null);
        Employee theEmployee = employeeRepository.findById(employeeId).orElse(null);
        Room theRoom = roomRepository.findByRoomName(roomName);

        if(roomIsAvailableForTime(theTimeSlot.getDate(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),roomName))
        {
            theTimeSlot.setTeams(Collections.singletonList(theTeams));
            if(capacityCheck(theTeams.getTeamCount(),roomName))
            {
                theTimeSlot.setTimeSlotId(0);
                theTimeSlot.setEmployee(theEmployee);
                theTimeSlot.setRooms(Collections.singletonList(theRoom));


                timeSlotRepository.save(theTimeSlot);
                return "Room was booked\n Unavailable members for meeting: \n"+availableEmployeeList(theTeams.getEmployees(),theTimeSlot);
            }
            else{

                return "team count exceeds room capacity \nNext nearest Room with capacity and date is :"+findNextNearestRoom(theTimeSlot,theTeams.getTeamCount());
            }

        }
        else {
            return "booking failed : room not available \nNext nearest Room with capacity and date is :" +findNextNearestRoom(theTimeSlot,theTeams.getTeamCount());
        }
    }

    /**
     * findNextNearestRoom method gives the room name which is available for the given date , time and capacity
     * @param timeSlot - time slot is passed as a TimeSlot object for which the rooms has to be found
     * @param teamCount - team count passed as integer to compare with the room capacity
     * @return - returns the Room name of type String which is available for the given date , time and capacity
     */
    public String findNextNearestRoom(TimeSlot timeSlot,int teamCount)
    {

        List<Room> allRooms = roomRepository.findAll();
        List<Room> availableRooms = allRooms.stream().map(theRoom->{
            Room returnRoom = null;
            if(roomIsAvailableForTime(timeSlot.getDate(),timeSlot.getStartTime(),timeSlot.getEndTime(),theRoom.getRoomName()))
            {
                if(capacityCheck(teamCount,theRoom.getRoomName()))
                {
                    returnRoom = theRoom;
                }
            }
            return returnRoom;
        }).collect(Collectors.toList());


        if(availableRooms==null)
        {
            return "No room is available for the specified date date, time and team capacity";
        }
        else {
            Collections.sort(availableRooms, Comparator.comparingInt(Room::getRoomCapacity));

            Room nearestRoom = availableRooms.get(0);

            return nearestRoom.getRoomName();
        }

    }

    /**
     * updateBooking updates the given timeSlot to the db
     * @param theTimeSlot -time slot of type TimeSlot
     * @param timeSlotId - employeeId of type integer
     * @return - Returns a string confirmation message of the update operation
     */
    public String updateBooking(TimeSlot theTimeSlot, int timeSlotId)
    {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);

        timeSlot.setTimeSlotId(timeSlotId);
        timeSlot.setStartTime(theTimeSlot.getStartTime());
        timeSlot.setEndTime(theTimeSlot.getEndTime());

        timeSlot.setRooms(timeSlot.getRooms());
        timeSlot.setEmployee(timeSlot.getEmployee());

        timeSlot.setDate(theTimeSlot.getDate());
        timeSlot.setBooked(theTimeSlot.getBooked());

        timeSlotRepository.save(timeSlot);
        return "Meeting details was updated on meeting id : "+timeSlotId;
    }

    /**
     * DeleteBooking method delete the time slot instance for the given id
     * @param timeSlotId - Time slot id of type integer for which the instance has to be deleted
     * @return - Returns message of type String for the confirmation of deleting
     */
    public String deleteBooking(int timeSlotId)
    {
        Optional<TimeSlot> result = timeSlotRepository.findById(timeSlotId);

        if(result.isPresent())
        {
            TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);

            LocalTime start_time=timeSlot.getStartTime();
            LocalTime currentTime=LocalTime.now();
            LocalTime newTime = start_time.minus(30, ChronoUnit.MINUTES);
            if (currentTime.isBefore(newTime)) {
                timeSlotRepository.deleteById(timeSlotId);
                return "Deleted time slot of id - " + timeSlotId;
            }
            else
            {
                return "can't cancel before 30mins of the meeting";
            }
        }
        else {
            throw new RuntimeException("Time slot id not found : "+timeSlotId);
        }
    }


    /**
     * capacityCheck method returns true when the room capacity is above the team count and returns false when the room capacity is below the team count
     * @param teamCount - team count parameter receives the team count as an integer
     * @param roomName - room name parameter receives the room name as a string
     * @return returns boolean true/false in case of room capacity exceeding to the team count
     */
    public boolean capacityCheck(int teamCount,String roomName)
    {
        Room theRoom = roomRepository.findByRoomName(roomName);
        return teamCount<= theRoom.getRoomCapacity();
    }

    public String availableEmployeeList(List<Employee> theEmployeeList,TimeSlot timeSlot)
    {
        List<Employee> updatedEmployeeList = theEmployeeList;

        for (Employee theEmployee:theEmployeeList) {
            List<Teams> empTeam = theEmployee.getTeams();
            for (Teams theTeam:empTeam) {
                if(teamsRepository.findByTimeSlotsAndTeamId(timeSlot,theTeam.getTeamId())!=null)
                {
                    updatedEmployeeList.add(theEmployee);
                }
            }
        }

        String unavailableEmployee="";
        for (Employee unAvailableEmp:updatedEmployeeList) {
            unavailableEmployee += unAvailableEmp.getFirstName() +" "+ unAvailableEmp.getLastName()+"\n";
        }

        return unavailableEmployee;
    }

    /**
     * roomIsAvailableForTime method returns true and returns false in case of the availability of rooms
     * @param date - date of type local date when meeting held
     * @param startTime - start time of type local time when the meeting held
     * @param endTime - end time of type local time when the meeting held
     * @param roomName - room name of type String for which the availability has to be checked
     * @return - returns boolean true/false of the availability of the room
     */
    public boolean roomIsAvailableForTime(LocalDate date, LocalTime startTime, LocalTime endTime, String roomName)
    {
        Room theRooms = roomRepository.findByRoomName(roomName);
        List<TimeSlot> theTimeSlot = timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(theRooms,date,endTime,startTime);
        return theTimeSlot.isEmpty();
    }

    /**
     * getTimeSlotResponse returns the entity with the type TimeSlotResponse to avoid Bi-directional looping
     * @param theTimeSlot - theTimeSlot with type TimeSlot is passed for which the response object has to be created
     * @return - returns the response with type TimeSlotResponse
     */
    public TimeSlotResponse getTimeSlotResponse(TimeSlot theTimeSlot)
    {
        TimeSlotResponse timeSlotResponse = new TimeSlotResponse(theTimeSlot.getTimeSlotId(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),theTimeSlot.getDate(),theTimeSlot.getBooked(),theTimeSlot.getDescription());

        List<Teams> timeSlotTeam = theTimeSlot.getTeams();
        List<TeamResponse> teamResponses =timeSlotTeam.stream().map(theTeams->{
            TeamResponse returnTeam = new TeamResponse(theTeams.getTeamId(),theTeams.getTeamName(),theTeams.getTeamCount());
            return returnTeam;
        }).collect(Collectors.toList());

        timeSlotResponse.setTeams(teamResponses);

        List<Room> timeSlotRooms = theTimeSlot.getRooms();
        List<RoomResponse> roomResponses = timeSlotRooms.stream().map(theRooms->{
            RoomResponse returnRoom = new RoomResponse(theRooms.getRoomId(),theRooms.getRoomName(),theRooms.getRoomCapacity());
            return returnRoom;
        }).collect(Collectors.toList());

        timeSlotResponse.setRooms(roomResponses);

        EmployeeResponse employeeResponse = new EmployeeResponse(theTimeSlot.getEmployee().getEmployeeId(),theTimeSlot.getEmployee().getFirstName(),theTimeSlot.getEmployee().getLastName(),theTimeSlot.getEmployee().getEmail());
        timeSlotResponse.setEmployee(employeeResponse);


        return timeSlotResponse;
    }

}
