
Sarah 'Hourly Chime' Plugin
===========================

Add this plugin to Sarah to enable an hourly chime.


Files
-----

The jar file built by this project needs to be copied to the Sarah plugins directory.
On my computer that directory is _/Users/al/Sarah/plugins/DDHourlyChime_.

Files in that directory should be:

    HourlyChime.info
    HourlyChime.jar
    README.txt

The _HourlyChime.info_ file currently contains these contents:

    main_class = com.devdaily.sarah.plugin.hourlychime.AkkaHourlyChimePlugin
    plugin_name = Hourly Chime


To-Do
-----

Nothing at this moment.


Developers - Building this Plugin
---------------------------------

You can build this plugin using the shell script named _build-jar.sh. It currently looks like this:

    #!/bin/bash

    sbt package

    if [ $? != 0 ]
    then
        echo "'sbt package' failed, exiting now"
        exit 1
    fi

    cp target/scala-2.10/hourlychime_2.10-0.1.jar HourlyChime.jar

    ls -l HourlyChime.jar

    echo ""
    echo "Created HourlyChime.jar. Copy that file to /Users/al/Sarah/plugins/DDHourlyChime, like this:"
    echo "cp HourlyChime.jar /Users/al/Sarah/plugins/DDHourlyChime"


Dependencies
------------

This plugin depends on:

* The Sarah2.jar file.
* The Akka/Scala actors. The actor version needs to be kept in sync with whatever actor version
  Sarah2 uses.







