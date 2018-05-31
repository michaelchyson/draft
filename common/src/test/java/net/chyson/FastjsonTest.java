package net.chyson;

import net.chyson.json.JsonUtils;

/**
 * michael.chyson
 * 5/31/2018
 */
public class FastjsonTest {

    public static void main(String[] args) {
        Conf parse = JsonUtils.parse("C:\\Users\\amain\\IdeaProjects\\project\\bjcmc-parent\\bjcmc-utils\\src\\main\\resources\\conf.json", Conf.class);
        System.out.println(parse.getHello());
        System.out.println(parse.getWorld());
    }
}
