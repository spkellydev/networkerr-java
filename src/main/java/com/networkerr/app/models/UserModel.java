package com.networkerr.app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.networkerr.core.dao.Model;
import io.netty.handler.codec.http.FullHttpRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserModel extends Model<UserSchema> {
    private UserSchema userSchema;
    public UserModel(String email, String password) {
        super( "users", "email");
        userSchema = new UserSchema(email, password);
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

    public Optional<UserSchema> get(String email) {
        ResultSet rs = this.select("email", this.table(), email);
        UserSchema userSchema = null;
        if (rs == null) {
            return Optional.empty();
        }
        String e = "";
        String p = "";
        String s = "";
        try {
            while (rs.next()) {
                int columns = rs.getMetaData().getColumnCount();
                for (int i = 1; i < columns + 1; i++) {
                    if (i == 2) {
                        e = rs.getObject(i).toString();
                    } else if (i == 3) {
                        p = rs.getObject(i).toString();
                    } else if (i == 4) {
                        s = rs.getObject(i).toString();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        userSchema = new UserSchema(e, p, s);
        return Optional.of(userSchema);
    }

    @Override
    protected List<UserSchema> getAll() {
        return null;
    }

    @Override
    public void save(UserSchema user) {
        String sql = String.format(
            "INSERT INTO %s (email, password, salt) VALUES ('%s', '%s', '%s');",
            this.table(),
            user.getEmail(),
            user.getPassword(),
            user.getSalt()
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
    public void setUserSchema(UserSchema userSchema) { this.userSchema = userSchema; }

    public String table() {
        return this.getTableName();
    }
}
