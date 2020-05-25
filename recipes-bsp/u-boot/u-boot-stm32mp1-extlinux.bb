SUMMARY = "Provide 'extlinux.conf' file for U-Boot"
LICENSE = "MIT"
# TODO: LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS += "u-boot-mkimage-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://boot.cmd"
SRC_URI += "file://extlinux.conf"

PV = "1.0"

B = "${WORKDIR}/build"

UBOOT_EXTLINUX_BOOTCMD = "${WORKDIR}/boot.cmd"
UBOOT_EXTLINUX_BOOTSCR = "${B}/boot.scr"
UBOOT_EXTLINUX_CONF = "extlinux.conf"

UBOOT_EXTLINUX_INSTALL_DIR ?= "/boot"

do_compile() {
    # Generate boot script only when multiple extlinux subdirs are set
    mkimage -C none -A ${UBOOT_ARCH} -T script -d ${UBOOT_EXTLINUX_BOOTCMD} ${UBOOT_EXTLINUX_BOOTSCR}
}

do_install() {
    install -d ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}
    # Install boot script
    if [ -e ${UBOOT_EXTLINUX_BOOTSCR} ]; then
        install -m 755 ${UBOOT_EXTLINUX_BOOTSCR} ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}
    fi
    # Install extlinux files
    if [ -e ${WORKDIR}/UBOOT_EXTLINUX_CONF ]; then
        cp ${WORKDIR}/UBOOT_EXTLINUX_CONF ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}
    fi
}
# TODO: FILES
FILES_${PN} = "${UBOOT_EXTLINUX_INSTALL_DIR}"
