package net.chyson.source;

import net.chyson.config.Config;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * michael.chyson
 * 5/28/2018
 */
public interface Sourcer {


    Object source();

//    Object preprocess();


}
