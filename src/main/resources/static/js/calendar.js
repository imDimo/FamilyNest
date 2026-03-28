let stompClient = null;

function main() {
    let bttn = document.getElementById("announceButton")
    bttn.addEventListener("click", function() {
        sendAnnouncement();
    })

    connect()
}

function sendAnnouncement() {
    let title = "Generic Announcement"
    let content = "Dinner at 5:00 PM"
    stompClient.publish({
        destination: "/app/announcement",
        body: JSON.stringify({"title" : title, "content" : content})
    })
}

function showAnnouncement(announcement) {
    let title = announcement.title
    let content = announcement.content
    let time = announcement.time

    let announcementArea = document.getElementById("announcementArea")

    let container = document.createElement("div");
    container.style.backgroundColor = "white"

    let header = document.createElement("h3")
    header.appendChild(document.createTextNode(title))

    let body = document.createElement("p")
    body.appendChild(document.createTextNode(content))

    let timestamp = document.createElement("p")
    timestamp.appendChild(document.createTextNode(`Sent at ${time}`))

    announcementArea.appendChild(container);
    container.appendChild(header)
    container.appendChild(body)
    container.appendChild(timestamp)
}

function connect() {
    // var socket = new SockJS("/announcement")
    let url = `ws://${location.host}/ws`;
    stompClient = new StompJs.Client({
        brokerURL: url,
        onConnect: () => {
            stompClient.subscribe("/topic/announcements", (timedAnnouncement) => {
                showAnnouncement(JSON.parse(timedAnnouncement.body))
            })
        }
    })

    stompClient.activate()
}

window.onload = (_) => {
    main()
}
