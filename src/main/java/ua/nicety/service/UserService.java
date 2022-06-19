package ua.nicety.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nicety.database.dao.UserDAO;
import ua.nicety.database.model.User;
import ua.nicety.http.dto.UserCreateEditDto;
import ua.nicety.http.mapper.UserCreateEditMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;
    private final UserCreateEditMapper userCreateEditMapper;


    public List<User> findAll() {
        return userDAO.showAll();
    }

    public Optional<User> findById(Long id) {
        return userDAO.findById(id);
    }

    public void create(UserCreateEditDto userDto) {
        Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map( user -> {
                    userDAO.save(user);
                    return user;
                });
    }


    @Transactional
    public boolean update(Long id, UserCreateEditDto userDto) {
        return userDAO.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto))
                .map(user -> {
                    userDAO.update(id, user);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public boolean delete(Long id) {
        return userDAO.findById(id)
                .map(entity -> {
                    userDAO.delete(id);
                    return true;
                })
                .orElse(false);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}

