/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webbfontaine.ephyto.config

/**
 *
 * @author sylla
 */
public enum Config {
    TECHNICAL('T'), DISABLED('D'), OPTIONAL('O'), CUSTOM('C'), MANDATORY('M'), PROHIBITED('P'), HIDDEN('H')

    private final label

    Config(String label) {
        this.label = label
    }

    boolean isConform(String label){
        this.label == label
    }
}    

