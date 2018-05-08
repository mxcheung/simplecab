	<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-antrun-plugin</artifactId>
				<version>1.8</version><!--$NO-MVN-MAN-VER$ -->
				<inherited>false</inherited>
				<executions>
					<execution>
						<phase>post-integration-test</phase>
						<goals>
							<goal>run</goal>
						</goals>
						<configuration>
							<target>
								<echo message="Generating JaCoCo Reports" />
								<taskdef name="report" classname="org.jacoco.ant.ReportTask">
									<classpath path="../target/jacoco-jars/org.jacoco.ant.jar" />
								</taskdef>
								<report>
									<executiondata>
										<fileset dir="./client/target">
											<include name="jacoco.exec" />
										</fileset>
										<fileset dir="./server/target">
											<include name="jacoco.exec" />
										</fileset>
									</executiondata>
									<structure name="simplecab Coverage Project">
										<group name="simplecab">
											<sourcefiles encoding="UTF-8">
												<fileset dir="./client/src/main/java" />
												<fileset dir="./server/src/main/java" />
											</sourcefiles>
											<classfiles>
												<fileset dir="./client/target/classes" />
												<fileset dir="./server/target/classes" />
											</classfiles>
										</group>
									</structure>
									<html destdir="${basedir}/target/jacoco-coverage-report/html" />
								</report>
							</target>
						</configuration>
					</execution>
				</executions>
				<dependencies>
					<dependency>
						<groupId>org.jacoco</groupId>
						<artifactId>org.jacoco.ant</artifactId>
						<version>0.8.1</version>
					</dependency>
				</dependencies>
			</plugin>
			
			
