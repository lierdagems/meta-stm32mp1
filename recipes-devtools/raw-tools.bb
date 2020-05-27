# This is copying form sdcard-raw-tools.bb of meta-st-stm32mp
SUMMARY = "Script for creating raw image ready to flash"
LICENSE = "MIT"
# TODO: LIC_FILES_CHKSUM
LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://create_flashlayout.sh"

BBCLASSEXTEND = "native nativesdk"

RDEPENDS_${PN}_append = "bash"

RRECOMMENDS_${PN}_append_class-nativesdk = "nativesdk-gptfdisk"

inherit deploy

SCRIPT_DEPLOYDIR ?= "scripts"

do_install() {
    install -d ${D}/${bindir}
    install -m 0755 ${WORKDIR}/create_flashlayout.sh ${D}/${bindir}
}

do_deploy() {
    :
}
do_deploy_class-native() {
    install -d ${DEPLOYDIR}/${SCRIPT_DEPLOYDIR}
    install -m 0755 ${WORKDIR}/create_flashlayout.sh ${DEPLOYDIR}/${SCRIPT_DEPLOYDIR}/
}
addtask deploy before do_build after do_compile
