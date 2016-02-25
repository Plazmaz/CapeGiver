/*
 * .  ____                 ____    ___
 *   / ___|___  _ __ ___  |___ \  / _ \
 *  | |   / _ \| '__/ _ \   __) || | | |
 *  | |__| (_) | | |  __/  / __/ | |_| |
 *   \____\___/|_|  \___| |_____(_)___/
 * This file is part of Core, a bukkit server framework for a minigame network.
 * Copyright (c) 2014. Lennart ten Wolde All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package me.plazmaz.bukkit.capegiver;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Dylan on 5/24/2015.
 */
public class CapeObject {
    public static Map<UUID, CapeObject> PLAYER_CAPES = new HashMap<>();
    public static Map<String, CapeObject> DEFAULT_CAPES = new HashMap<>();
    private String value, signature;

    public static void init(CapeGiver plugin) {
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(plugin.getResource("capes.yml"));
        for(String key : defaultConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            CapeObject cape = new CapeObject(defaultConfig.getString(key + ".value"), defaultConfig.getString(key + ".signature"));
            PLAYER_CAPES.put(uuid, cape);
        }
        DEFAULT_CAPES.put("2011", new CapeObject("eyJ0aW1lc3RhbXAiOjE0NTYzNzI4ODgzNTAsInByb2ZpbGVJZCI6Ijg2NjdiYTcxYjg1YTQwMDRhZjU0NDU3YTk3MzRlZWQ3IiwicHJvZmlsZU5hbWUiOiJTdGV2ZSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU2ZWVjMWMyMTY5YzhjNjBhN2FlNDM2YWJjZDJkYzU0MTdkNTZmOGFkZWY4NGYxMTM0M2RjMTE4OGZlMTM4In0sIkNBUEUiOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iNzY3ZDQ4MzI1ZWE1MzI0NTYxNDA2YjhjODJhYmJkNGUyNzU1ZjExMTUzY2Q4NWFiMDU0NWNjMiJ9fX0=",
                "vOMrv9gMfa5gs0UifgIgKAuYvPoWlYzYFqn+3Tx1Du7ypkLA1aAFuDH3MvC7sjBrpbS0N/VG39La/Y1Zwom3J5fTC83kxy4sUm0MrZkFLsQva4tTlTMQkYzZB+3jIAYTV+G5yglwr1DAHSDS8ajcg8eE0rdL7YKnVM1yYNPof9d+sA6QggysQK8UyplXsFnS37Y6B71n2QKJNzx04QKa4quEY1Oghd7/gMM+PqRW/PBuzXpGDw1nn+bP6RHbyu+iqB5306LSHTlCjyv2/pj0BYVISJiz5mh8NiaM+QtXNnRAu94i0Vz5XNA5Aw4tqJz3O7t+CgDCZsIixfbA9u7EBDm1abcRx1iuQliD5plU5U5bWPLR4QJqSi6Ru8240vkdbxaK17nxgiQboiMqQfarJh9G4FMZYPqTQf+5PRfA7s1mj4bX1d9NR4/hP+NDXOyRi7cR8/GR3TppzUCZswbmEPU50ymmg3si+WQfx9Gwj56Ru3wdWDtm8wC0ae89GxMmVa8iI2eTZ0vlH5+NX4fJaDrA127yAACI0CpuxfnwQdypgwo87LNdhrwDo84DzaWoEcJPZRNDIZiPBCG3b4nqjtoydNU/zJt8LmjOcNFIqlvtJu97acCKQCfTEXViMEBsC1wXChNLycO4OrfEr4eh4RS6oQ7naBukBOzjaPiGdSY="));
        DEFAULT_CAPES.put("2015", new CapeObject("eyJ0aW1lc3RhbXAiOjE0NTYzODIwNDY1OTksInByb2ZpbGVJZCI6ImNkNTIxOTIzMzg1ZTRlY2ZhNDM5YjI2YWEyZDQwMmZiIiwicHJvZmlsZU5hbWUiOiJEeWxhbkJ5dGVzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IkNBUEUiOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMmI5YzVlYTc2M2M4NmZjNWNhZWEzM2Q4MmIwZmE2NWE3YzIyOGZkMzIxYmE1NDc2NmVhOTVhM2QwYjk3OTMifX19",
                "WJHUkLngK5IjubvOpaDsA7ub42wt9XMsCvLoMh6uZUkdG7Di2lA/lB8C/l8KV7d84wmxHKUfFIxMX/149MPiyNLIGWBeSuFA2UanP/m+M/Jv+79vPaQA+5KTFaHs3OOw5d9WRHQVTKbDP+xxUXLjJ3758OBaxhku9/MsLpYh27vK4nvv0jXWYcymATcg2Q8TJCEiRNBNo21gkZg2aWdbpETwD7kBuE1J6hASuSCVNYVVOuDJi/S6jOQUtgs/fMcQPthA9YTZFAx5AnHkKHdZLb41algIWQ0fITrkhJ1JWoZAUnrCm3TAc1Kdg1s/3fsk7x/tetuef+K/62ChMOaXPfW6MrIrqcqXD4ZECoDCV6WrnRjr5moYbVszlGijMzL8z0aW1THu3dGBCL4qeR54+OO9IzjN/p3rDSLqXW1ub4d1IT4M4PmPsAxfXfSrYJpqMJBGAQn4QSQXlZJOLZ9qfhpmLZgujkXUSkRuRjj83NiZTkXnaORXXiizS88KMo5DhP0/53AxH8sXmX/xef87McyRO7nX6Euwt3TTtfD2qwwqXJMhP3NICv5/6XRFGtkX+Ttgvm8h8Dt6GEE6A8hOZBGYon4TKtLbJdlPPSDU15QdUFQyGUdHSsZhYWqP+0hNtDEn1Q2k6JyqlYVSVsMzGh9LEBXbtpOIzp93iM5cWxM="));


    }

    public CapeObject(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
