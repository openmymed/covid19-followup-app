/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmymed.accessmd.domain.auth.enums;

import io.javalin.core.security.Role;

/**
 *
 * @author tareq
 */
public enum UserRole implements Role {
  ROLE_DOCTOR,
  ROLE_PATIENT,
  ROLE_ADMIN,
  NONE
}
