package com.networkerr.app.models;

import com.networkerr.core.dao.Model;

import java.util.List;
import java.util.Optional;

public class UserModel extends Model<UserSchema> {
    @Override
    protected Optional<UserSchema> get(long id) {
        return Optional.empty();
    }

    @Override
    protected List<UserSchema> getAll() {
        return null;
    }

    @Override
    protected void save(UserSchema userSchema) {

    }

    @Override
    protected void update(UserSchema userSchema, String[] params) {

    }

    @Override
    protected void delete(UserSchema userSchema) {

    }
}
