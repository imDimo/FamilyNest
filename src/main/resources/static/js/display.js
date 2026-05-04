function showAnnouncement(announcement) {
    let announcementArea = document.getElementById("global-announcement-area");

    if (!announcementArea) {
        announcementArea = document.createElement("div");
        announcementArea.id = "global-announcement-area";
        document.body.appendChild(announcementArea);
    }

    let container = document.createElement("div");
    container.className = "announcement-card";

    let closeBtn = document.createElement("button");
    closeBtn.innerHTML = "&times;";
    closeBtn.className = "close-announcement";
    closeBtn.onclick = () => container.remove();

    let header = document.createElement("h3");
    header.textContent = announcement.title;

    let body = document.createElement("p");
    body.textContent = announcement.content;

    let timestamp = document.createElement("small");
    timestamp.textContent = `Sent at ${announcement.time}`;

    container.append(closeBtn, header, body, timestamp);
    announcementArea.appendChild(container);


    setTimeout(() => { if(container) container.remove(); }, 10000);
}

function initGlobalAnnouncements() {
    let url = `ws://${location.host}/ws`;
    let client = new StompJs.Client({
        brokerURL: url,
        onConnect: () => {
            client.subscribe("/topic/announcements", (message) => {
                showAnnouncement(JSON.parse(message.body));
            });
        }
    });
    client.activate();
}
