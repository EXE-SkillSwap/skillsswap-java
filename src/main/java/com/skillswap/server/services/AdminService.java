package com.skillswap.server.services;

import java.util.Map;

public interface AdminService {

    Map<String, Object> getStatistics();

    Map<String, Object> getRevenue();
}
