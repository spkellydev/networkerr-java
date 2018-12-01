package com.networkerr.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.networkerr.core.dao.Model;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Optional;

public class UserModel extends Model<UserSchema> {
    private UserSchema userSchema;
    public UserModel(String email, String password) {
        super( "users", "email");
        userSchema = new UserSchema("spkellydev@email.com", "password");
    }

    public UserModel(FullHttpRequest msg) {
        super("users", "email");
        JsonNode payload = this.getMappable(msg);
        userSchema = new UserSchema(payload.get("email").asText(), payload.get("password").asText());
    }

    @Override
    protected Optional<UserSchema> get(long id) {
        return Optional.empty();
    }

    @Override
    protected List<UserSchema> getAll() {
        return null;
    }

    @Override
    public void save(UserSchema user) {
        String sql = String.format(
            "INSERT INTO %s (email, password) VALUES ('%s', '%s');",
            this.table(),
            user.getEmail(),
            user.getPassword()
        );
        this.execute(sql);
    }

    @Override
    protected void update(UserSchema userSchema, String[] params) {

    }

    @Override
    protected void delete(UserSchema userSchema) {

    }

    public String writeAsString() {
        return this.jsonStringify(userSchema);
    }

    public UserSchema schema() {
        return this.userSchema;
    }

    public String table() {
        return this.getTableName();
    }
}
