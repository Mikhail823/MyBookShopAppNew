package com.example.bookshop.repository;

import com.example.bookshop.struct.enums.ContactType;
import com.example.bookshop.struct.user.UserContactEntity;
import com.example.bookshop.struct.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserContactRepository extends JpaRepository<UserContactEntity, Integer> {

    UserContactEntity findFirstByContactAndType(String contact, ContactType type);

    UserContactEntity findUserContactEntityByCode(String code);

    UserContactEntity findFirstByContact(String contact);

    List<UserContactEntity> findUserContactEntitiesByUserId(UserEntity userId);

    UserContactEntity findFirstUserContactEntityByUserIdAndType(UserEntity user, ContactType type);


}
