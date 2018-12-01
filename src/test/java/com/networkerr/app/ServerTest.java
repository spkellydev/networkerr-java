package com.networkerr.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServerTest {
    @Test
    public void serverShouldBoot() {
        Server.main(new String[] {"dev"});

    }
}