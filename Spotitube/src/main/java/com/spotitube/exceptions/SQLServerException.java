package com.spotitube.exceptions;

import java.sql.SQLException;

public class SQLServerException extends RuntimeException {
    public SQLServerException(SQLException message) {
        super(message);
    }
}
