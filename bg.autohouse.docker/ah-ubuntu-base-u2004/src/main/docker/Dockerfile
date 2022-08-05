# Ubuntu base image

FROM ubuntu:${docker.image.base.tag}

ARG GOSU_VERSION=1.14

ENV DEBIAN_FRONTEND noninteractive

# Install needed utilities
RUN apt-get update \
    && apt-get -qq --no-install-recommends -y install \
    wget \
    libgnutls-openssl27 \
    iputils-ping \
    psmisc \
    curl \
    telnet \
    zip unzip \
    tzdata \
    cifs-utils \
    locales \
    && locale-gen en_US.UTF-8 \
    && locale-gen de_DE.UTF-8 \
    && rm -rf /etc/default/locale \
    && apt-get -y autoremove \
    && apt-get -y autoclean \
    && rm -rf /var/lib/apt/lists/*

RUN echo "\
            \nLANG=en_US.UTF-8 \
            \nLANGUAGE=en_US.UTF-8 \
            \nLC_CTYPE=UTF-8 \
            \nLC_COLLATE=en_US.UTF-8 \
            \nLC_MESSAGES=en_US.UTF-8 \
            \nLC_ALL=en_US.UTF-8 \
        " >> /etc/environment

ENV LANG="en_US.UTF-8" \
    LANGUAGE="en_US.UTF-8" \
    LC_CTYPE="UTF-8" \
    LC_COLLATE="en_US.UTF-8" \
    LC_MESSAGES="en_US.UTF-8" \
    LC_ALL="en_US.UTF-8"

# grab gosu for easy step-down from root
RUN set -eux; \
  # save list of currently installed packages for later so we can clean up
	savedAptMark="$(apt-mark showmanual)"; \
	apt-get update; \
	apt-get install -y --no-install-recommends ca-certificates wget; \
	if ! command -v gpg; then \
		apt-get install -y --no-install-recommends gnupg2 dirmngr; \
	elif gpg --version | grep -q '^gpg (GnuPG) 1\.'; then \
  # "This package provides support for HKPS keyservers." (GnuPG 1.x only)
		apt-get install -y --no-install-recommends gnupg-curl; \
	fi; \
	rm -rf /var/lib/apt/lists/*; \
	\
	dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')"; \
	wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch"; \
	wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc"; \
	\
  # verify the signature
	export GNUPGHOME="$(mktemp -d)"; \
	gpg --batch --keyserver hkps://keys.openpgp.org --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4; \
	gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu; \
	command -v gpgconf && gpgconf --kill all || :; \
	rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc; \
	\
  # clean up fetch dependencies
	apt-mark auto '.*' > /dev/null; \
	[ -z "$savedAptMark" ] || apt-mark manual $savedAptMark; \
	apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false; \
	\
	chmod +x /usr/local/bin/gosu; \
  # verify that the binary works
	gosu --version; \
	gosu nobody true


# show available locales
RUN locale -a

# this must be UTC
ARG TIMEZONE=UTC

RUN ln -fs /usr/share/zoneinfo/${TIMEZONE} /etc/localtime \
    && dpkg-reconfigure -f noninteractive tzdata
