FROM debian:latest

ENV JRUBY_VERSION 1.7.15
ENV STOKESDRIFT_USER stokesdrift
ENV STOKESDRIFT_VERSION 0.1.7

/opt/jruby/lib/ruby/gems/shared/gems/stokes-drift-$STOKESDRIFT_VERSION

# Setup user
RUN groupadd -r $STOKESDRIFT_USER -g 433 && \
    useradd -u 431 -r -g $STOKESDRIFT_USER -d /tmp -s /sbin/nologin -c "Docker image user" $STOKESDRIFT_USER

RUN apt-get update
# Setup git
RUN apt-get install -y --no-install-recommends ssh
RUN apt-get install -y --no-install-recommends wget
RUN apt-get install -y --no-install-recommends git
RUN apt-get install -y --no-install-recommends unzip
RUN mkdir -p /root/.ssh/
ADD .ssh/id_rsa /root/.ssh/id_rsa
RUN chmod 700 /root/.ssh/id_rsa && \
    echo "Host github.com\n\tStrictHostKeyChecking no\n" >> /root/.ssh/config

# Setup Jruby env
# TODO Java Policy files?
RUN apt-get install -y --no-install-recommends openjdk-7-jre-headless tar curl && apt-get autoremove -y && apt-get clean
RUN curl http://jruby.org.s3.amazonaws.com/downloads/$JRUBY_VERSION/jruby-bin-$JRUBY_VERSION.tar.gz | tar xz -C /opt
RUN /bin/ln -s /opt/jruby-$JRUBY_VERSION /opt/jruby
ENV PATH /opt/jruby/bin:$PATH
RUN echo gem: --no-document >> /etc/gemrc
RUN gem update --system
RUN gem install bundler

# Set specific envs
ENV JAVA_HOME="/usr/lib/jvm/java-1.7.0-openjdk-amd64"
ENV JRUBY_HOME=/opt/jruby/
ENV STOKESDRIFT_DIR=/opt/jruby/lib/ruby/gems/shared/gems/stokes-drift-$STOKESDRIFT_VERSION

# TODO set GEM PATH?

# SET PERMISSIONS
RUN chown -R $STOKESDRIFT_USER:$STOKESDRIFT_USER /opt/jruby/

# CLEAN UP
RUN apt-get -y remove git
RUN rm /root/.ssh/id_rsa

USER $STOKESDRIFT_USER
ENTRYPOINT /opt/jruby/lib/ruby/gems/shared/gems/stokes-drift-$STOKESDRIFT_VERSION/server_startup.sh
