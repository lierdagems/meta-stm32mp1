SUMMARY = "Universal Boot Loader Splash Screen for stm32mp1 embedded devices"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://logo.bmp"

B = "${WORKDIR}/build"

PV = "1.0"

UBOOT_SPLASH_LOGO = "logo.bmp"
UBOOT_SPLASH_IMAGE ?= "splash"

inherit deploy

# TODO: jpg/png--->bmp
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/boot
    if [ -e "${B}/${UBOOT_SPLASH_LOGO}" ]; then
        install -m 644 ${B}/${UBOOT_SPLASH_LOGO} ${D}/boot/${UBOOT_SPLASH_IMAGE}.bmp
    fi
}

# TODO: ALLOW_EMPTY
ALLOW_EMPTY_${PN} = "1"
FILES_${PN} = "/boot"
