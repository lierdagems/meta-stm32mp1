SUMMARY = "Provide 'extlinux.conf' file for U-Boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "u-boot-mkimage-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://boot.cmd"
SRC_URI += "file://extlinux.conf"

PV = "1.0"

inherit kernel-arch

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
        install -m 755 ${UBOOT_EXTLINUX_BOOTSCR} ${D}${UBOOT_EXTLINUX_INSTALL_DIR}
    fi
    # Install extlinux files
    install -d ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}/mmc0_${UBOOT_DEVICETREE}_extlinux/
    install -d ${D}/${UBOOT_EXTLINUX_INSTALL_DIR}/mmc1_${UBOOT_DEVICETREE}_extlinux/
    cp ${WORKDIR}/${UBOOT_EXTLINUX_CONF} ${D}${UBOOT_EXTLINUX_INSTALL_DIR}/mmc0_${UBOOT_DEVICETREE}_extlinux/
    cp ${WORKDIR}/${UBOOT_EXTLINUX_CONF} ${D}${UBOOT_EXTLINUX_INSTALL_DIR}/mmc1_${UBOOT_DEVICETREE}_extlinux/

}
# TODO: FILES
FILES_${PN} = "${UBOOT_EXTLINUX_INSTALL_DIR}"
