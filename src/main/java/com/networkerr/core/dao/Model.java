package com.networkerr.core.dao;

import java.util.List;
import java.util.Optional;

public abstract class Model<T> {
    protected Database db = null;
    protected Model() {
        db = Database.getInstance();
        db.connect("networkerr", "root", "");
    }

    protected void execute(String sql) {
        this.db.execute(sql);
    }

    protected abstract Optional<T> get(long id);

    protected abstract List<T> getAll();

    protected abstract void save(T t);

    protected abstract void update(T t, String[] params);

    protected abstract void delete(T t);
}
