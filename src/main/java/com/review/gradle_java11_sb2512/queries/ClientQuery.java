package com.review.gradle_java11_sb2512.queries;

public class ClientQuery {

    public final static String FIND_PERSON_JOIN_CLIENT = "" +
            "SELECT home_address, cast(date_of_birth as varchar) date_of_birth" +
            ", identification, last_name, name, phone, work_address, client_number, person_id" +
            " FROM persons p " +
            " INNER JOIN clients c ON p.id = c.person_id ";

}
