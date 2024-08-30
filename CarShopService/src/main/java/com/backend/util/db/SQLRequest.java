package com.backend.util.db;

public class SQLRequest {

    public static final String INSERT_ALL_INTO_CAR = "INSERT INTO  main.car VALUES (DEFAULT,?,?,?,?,?,?,?)";
    public static final String INSERT_ALL_INTO_USER = "insert into main.user values(DEFAULT,?,?,?,?,?,?)";
    public static final String INSERT_ALL_INTO_ORDER = "insert into main.order values(DEFAULT,?,?,?,?,?,?,?)";
    public static final String INSERT_ALL_INTO_ACTION = "INSERT INTO  main.action VALUES (DEFAULT,?,?,?,?)";

    public static final String SELECT_ALL_FROM_CAR = "SELECT * FROM  main.car";
    public static final String SELECT_ALL_FROM_ORDER = "SELECT * FROM  main.order";
    public static final String SELECT_ALL_FROM_ACTION = "SELECT * FROM  main.action";
    public static final String SELECT_ALL_FROM_USER = "SELECT * FROM main.user";

    public static final String SELECT_ALL_CLIENTS = "SELECT * FROM  main.action";
    public static final String SELECT_CLIENTS_BY_MANAGER = """
            SELECT distinct cli.id,
                   cli.username,
                   cli.password,
                   cli.role,
                   cli.name,
                   cli.email,
                   cli.phone
            FROM main.order
            LEFT JOIN main."user" man on man.id = "order".manager
            LEFT JOIN main."user" cli on cli.id = "order".client
            WHERE man.username = ?""";

    public static final String SELECT_CAR_BY_ID = "SELECT * FROM main.car WHERE id = ?";
    public static final String DELETE_CAR_BY_ID = "DELETE FROM main.car WHERE id = ?";
    public static final String SELECT_CAR_BY_CLIENT = """
                SELECT c.id,
                       c.brand,
                       c.model,
                       c.year,
                       c.price,
                       c.condition,
                       c.color,
                       c.availability
                FROM main."order" o
                         JOIN main.car c ON o.car = c.id
                         JOIN main."user" u ON o.client = u.id
                WHERE u.username = ?;""";


    ;
}
