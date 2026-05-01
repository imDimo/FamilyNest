let stompClient = null;

function main() {
    let bttn = document.getElementById("announceButton")
    bttn.addEventListener("click", function() {
        sendAnnouncement();
    })

    connect()
}

function sendAnnouncement() {
    let title = document.getElementById("announcementTitle").value || "No Title";
    let content = document.getElementById("announcementContent").value || "No Content";
    stompClient.publish({
        destination: "/app/announcement",
        body: JSON.stringify({"title" : title, "content" : content})
    })
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
