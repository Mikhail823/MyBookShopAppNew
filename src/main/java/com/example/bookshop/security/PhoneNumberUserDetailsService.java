package com.example.bookshop.security;

import com.example.bookshop.struct.user.UserContactEntity;
import com.example.bookshop.struct.user.UserEntity;

public class PhoneNumberUserDetailsService extends BookstoreUserDetails{

    public PhoneNumberUserDetailsService(UserContactEntity userContact) {
        super(userContact);
    }

    @Override
    public String getUsername() {
        return getContact().getContact();
    }
}
