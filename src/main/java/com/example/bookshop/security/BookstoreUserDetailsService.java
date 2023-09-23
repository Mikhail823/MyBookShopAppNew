package com.example.bookshop.security;

import com.example.bookshop.repository.UserContactRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.security.exception.UserNotFoundException;
import com.example.bookshop.struct.enums.ContactType;
import com.example.bookshop.struct.user.UserContactEntity;
import com.example.bookshop.struct.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookstoreUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserContactRepository contactRepository;

    @Override
    public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {

        UserContactEntity userContact = contactRepository.findFirstByContactAndType(contact, ContactType.EMAIL);
        UserEntity user = userRepository.findUserEntityById(userContact.getUserId().getId());

        if (user != null){
            return new BookstoreUserDetails(userContact);
        }

        userContact = contactRepository.findFirstByContactAndType(contact, ContactType.PHONE);
        user = userRepository.findUserEntityById(userContact.getUserId().getId());
        if (user != null){
            return new PhoneNumberUserDetailsService(userContact);
        }
        else {
            throw new UserNotFoundException("Пользователь не зарегистрирован!!!");
        }
    }

//    public void processOAuthPostLogin(String email, String name) {
//
//        UserEntity existUser = userRepository.findUserEntityByEmail(email);
//
//        if (existUser == null) {
//            UserEntity newUser = new UserEntity();
//            newUser.setEmail(email);
//            newUser.setName(name);
//            userRepository.save(newUser);
//        }
  //  }
}
