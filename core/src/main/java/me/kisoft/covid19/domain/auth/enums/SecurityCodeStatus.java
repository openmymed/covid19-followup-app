/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.kisoft.covid19.domain.auth.enums;

/**
 *
 * @author tareq
 */
public enum SecurityCodeStatus {
    VALID,
    CONSUMED,
    INVALIDATED,
    TIMEOUT
}
