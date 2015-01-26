#!/bin/bash


for i in $STOKESDRIFT_LIB_DIR/*.jar; do
    CLASSPATH=$CLASSPATH:$i
done
export CLASSPATH=`echo $CLASSPATH | cut -c2-`


JAVA_OPTS="-Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Xms64m -Xmx512m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true"

echo $STOKESDRIFT_DIR
echo $STOKESDRIFT_LIB_DIR

java $JAVA_OPTS org.stokesdrift.Server

#         ROOT_PATH(new String[]{ "-r","-root_path" }, null),
#        CONFIG_FILE(new String[]{ "-c","-config_file" }, "drift_config.yml"),