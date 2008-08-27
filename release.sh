#!/bin/sh
exec mvn -DupdateReleaseInfo=true clean source:jar javadoc:jar deploy
