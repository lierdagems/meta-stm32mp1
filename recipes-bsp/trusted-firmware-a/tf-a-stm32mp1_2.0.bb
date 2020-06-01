SUMMARY = "Trusted Firmware-A recipe"
SECTION = "bootloaders"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.rst;md5=e927e02bca647e14efd87e9e914b2443"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS += "dtc-native"

inherit deploy

# SRC_URI
SRC_URI = "git://github.com/lierdagems/arm-trusted-firmware.git"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# Extra make settings
EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX}'
EXTRA_OEMAKE += 'PLAT=stm32mp1'
EXTRA_OEMAKE += 'ARCH=aarch32'
EXTRA_OEMAKE += 'ARM_ARCH_MAJOR=7'
EXTRA_OEMAKE += 'AARCH32_SP=sp_min'

# Debug support
EXTRA_OEMAKE += 'DEBUG=0'
EXTRA_OEMAKE += "LOG_LEVEL=40"

STAGING_TFA_DIR = "${TMPDIR}/work-shared/${MACHINE}/tfa-source"

# this is copying form kernel.bbclass
# Make sure to move ${S} to STAGING_TFA_DIR. We can't just
# create the symlink in advance as the git fetcher can't cope with
# the symlink.
do_unpack[cleandirs] += " ${S} ${STAGING_TFA_DIR}"
do_clean[cleandirs] += " ${S} ${STAGING_TFA_DIR}"

# this is copying form kernel.bbclass
base_do_unpack_append () {
    # Copy/Paste from kernel class with adaptation to TFA var
    s = d.getVar("S")
    if s[-1] == '/':
        # drop trailing slash, so that os.symlink(tfasrc, s) doesn't use s as directory name and fail
        s=s[:-1]
    tfasrc = d.getVar("STAGING_TFA_DIR")
    if s != tfasrc:
        bb.utils.mkdirhier(tfasrc)
        bb.utils.remove(tfasrc, recurse=True)
        if d.getVar("EXTERNALSRC"):
            # With EXTERNALSRC S will not be wiped so we can symlink to it
            os.symlink(s, tfasrc)
        else:
            import shutil
            shutil.move(s, tfasrc)
            os.symlink(tfasrc, s)
}

do_compile() {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS

    oe_runmake -C ${S} DTB_FILE_NAME=${TF_A_DEVICETREE}.dtb BUILD_PLAT=${B}
}

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 644 ${B}/tf-a-${TF_A_DEVICETREE}.stm32 ${DEPLOYDIR}/
}
addtask deploy before do_build after do_compile

