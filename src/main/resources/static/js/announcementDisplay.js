function showAnnouncement(message) {
    const popup = document.getElementById('announcementPopup');
    const msgSpan = document.getElementById('announcementMessage');
    msgSpan.textContent = message;
    popup.style.display = 'flex';
}

function hideAnnouncement() {
    document.getElementById('announcementPopup').style.display = 'none';
}

document.addEventListener('DOMContentLoaded', () => {
    const closeBtn = document.getElementById('closeAnnouncement');
    if (closeBtn) {
        closeBtn.addEventListener('click', hideAnnouncement);
    }
});

window.showAnnouncement = showAnnouncement;
window.hideAnnouncement = hideAnnouncement;