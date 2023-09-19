//package cdw.springProject.ticketBooking.config;
//
//import cdw.springProject.ticketBooking.dao.UserRepository;
//import cdw.springProject.ticketBooking.entity.Role;
//import cdw.springProject.ticketBooking.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserInfoDetailsService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    @Autowired
//    public UserInfoDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByMail(username);
//        return new org.springframework.security.core.userdetails.User(user.getMail(),user.getPassword(),mapRoles(user.getRoles()));
//    }
//
//    private Collection<GrantedAuthority> mapRoles(List<Role> roles)
//    {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//    }
//}
