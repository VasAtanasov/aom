# Ubuntu base image

FROM ubuntu:${BASE_IMAGE_TAG}

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

# show available locales
RUN locale -a

# this must be UTC
ARG TIMEZONE=UTC

RUN ln -fs /usr/share/zoneinfo/${TIMEZONE} /etc/localtime \
    && dpkg-reconfigure -f noninteractive tzdata
