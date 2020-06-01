LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

require recipes-kernel/linux/linux-yocto.inc

# SRC_URI
SRC_URI = "git://github.com/lierdagems/linux-stm32mp1.git"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

do_configure() {
    oe_runmake ${KBUILD_DEFCONFIG} -C ${S} O=${B}
}

MODULE_TARBALL_DEPLOY = "0"










