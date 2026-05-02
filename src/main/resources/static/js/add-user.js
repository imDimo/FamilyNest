
function main() {
    let form = document.getElementById("add-user-form")
    let name_input = document.getElementById("username")
    let error_elem = document.getElementById("error-container")
    let pass_input = document.getElementById("password")

    form.addEventListener("submit", (e) => {

        if (name_input.value.trim().length == 0) {
            e.preventDefault()

            console.log("Username was empty" + name_input.textContent.trim())
            error_elem.replaceChildren()
            let info = document.createElement("p")
            info.textContent="Invalid Username"
            error_elem.appendChild(info)
            return
        }

        if (pass_input.value.trim().length == 0) {
            e.preventDefault()

            console.log("Password was empty" + pass_input.textContent.trim())
            error_elem.replaceChildren()
            let info = document.createElement("p")
            info.textContent="Invalid Password"
            error_elem.appendChild(info)
            return
        }
    })
}







window.onload = (_) => {
    main()
}
