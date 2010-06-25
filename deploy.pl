#!/usr/bin/perl

$webapps_dir = "/var/lib/tomcat6/webapps";
print "Building war...\n";
`grails prod war`;
print "Copying war...\n";
`sudo rm -rf $webapps_dir/ROOT`;
`sudo mkdir $webapps_dir/ROOT`;
`sudo cp target/routemap-0.1.war $webapps_dir/ROOT/`;
chdir("$webapps_dir/ROOT");
`sudo unzip routemap-0.1.war`;
print "Deployment complete.\n";
exit 0;
