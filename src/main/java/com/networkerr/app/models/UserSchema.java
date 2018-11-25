package com.networkerr.app.models;

import com.networkerr.core.dao.Schema;

public interface UserSchema extends Schema {
    String email = null;
    String password = null;
    String validatePassword();
}
