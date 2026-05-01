function showAnnouncement(announcement) {
    let title = announcement.title
    let content = announcement.content
    let time = announcement.time

    let announcementArea = document.getElementById("announcement-popup-area");

    let container = document.createElement("div");
    container.className = "floating-announcement";

    let closeBtn = document.createElement("button");
    closeBtn.innerHTML = "&times;";
    closeBtn.className = "close-btn";
    closeBtn.onclick = function() { container.remove(); };

    let header = document.createElement("h3");
    header.appendChild(document.createTextNode(title));

    let body = document.createElement("p");
    body.appendChild(document.createTextNode(content));

    let timestamp = document.createElement("p");
    timestamp.style.fontSize = "0.8em";
    timestamp.appendChild(document.createTextNode(`Sent at ${time}`));

    container.appendChild(closeBtn);
    container.appendChild(header);
    container.appendChild(body);
    container.appendChild(timestamp);
    
    announcementArea.appendChild(container);
}
