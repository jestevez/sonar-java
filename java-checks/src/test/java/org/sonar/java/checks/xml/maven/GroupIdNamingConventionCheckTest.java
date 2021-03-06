/*
 * SonarQube Java
 * Copyright (C) 2012-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.checks.xml.maven;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

public class GroupIdNamingConventionCheckTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_default() {
    GroupIdNamingConventionCheck check = new GroupIdNamingConventionCheck();
    SonarXmlCheckVerifier.verifyIssues("defaultNOK/pom.xml", check);
    SonarXmlCheckVerifier.verifyIssues("emptyGroupId/pom.xml", check);
    SonarXmlCheckVerifier.verifyNoIssue("defaultOK/pom.xml", check);
    SonarXmlCheckVerifier.verifyNoIssue("noGroupId/pom.xml", check);
  }

  @Test
  public void test_custom() {
    GroupIdNamingConventionCheck check = new GroupIdNamingConventionCheck();
    check.regex = "[a-z][a-z-0-9]*";
    SonarXmlCheckVerifier.verifyIssues("customNOK/pom.xml", check);
    SonarXmlCheckVerifier.verifyIssues("emptyGroupId/pom.xml", check);
    SonarXmlCheckVerifier.verifyNoIssue("customOK/pom.xml", check);
    SonarXmlCheckVerifier.verifyNoIssue("noGroupId/pom.xml", check);
  }

  @Test
  public void invalid_regex() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("[S3419] Unable to compile the regular expression: *");

    GroupIdNamingConventionCheck check = new GroupIdNamingConventionCheck();
    check.regex = "*";
    SonarXmlCheckVerifier.verifyNoIssue("defaultNOK/pom.xml", check);
  }

  @Test
  public void not_a_pom() {
    GroupIdNamingConventionCheck check = new GroupIdNamingConventionCheck();
    SonarXmlCheckVerifier.verifyNoIssue("../irrelevant.xml", check);
  }
}
