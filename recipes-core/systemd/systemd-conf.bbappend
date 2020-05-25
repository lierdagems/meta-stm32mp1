do_install_append() {
    # enable watchdog on systemd configuration
    sed -e 's|^[#]*RuntimeWatchdogSec.*|RuntimeWatchdogSec=30|g' -i ${D}${sysconfdir}/systemd/system.conf
    sed -e 's|^[#]*ShutdownWatchdogSec.*|ShutdownWatchdogSec=5min|g' -i ${D}${sysconfdir}/systemd/system.conf
}
