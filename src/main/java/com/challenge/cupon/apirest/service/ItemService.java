package com.challenge.cupon.apirest.service;

import java.util.List;
import java.util.Map;

public interface ItemService {
    public Map<String, Float> buildItems(List<String> itemsToSearch);
    public Float sumItems(List<String>itemsToSum);
}
