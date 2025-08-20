package com.mini_banking.server.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.mini_banking.server.entity.Account;

public class AccountSpecification {

  public static Specification<Account> belongsToUser(String username) {
    return (root, query, cb) -> cb.equal(root.get("user").get("username"), username);
  }

  public static Specification<Account> hasNumber(String number) {
    return (root, query, cb) -> {
      if (number == null || number.isEmpty()) {
        return cb.conjunction();
      }
      return cb.like(cb.lower(root.get("number")), "%" + number.toLowerCase() + "%");

    };
  }

  public static Specification<Account> hasName(String name) {
    return (root, query, cb) -> {
      if (name == null || name.isEmpty()) {
        return cb.conjunction();
      }
      return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");

    };
  }
}
