document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("disputeForm");

    if (!form) return;

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const data = {
            transactionId: document.getElementById("transactionId").value.trim(),
            reason: document.getElementById("reason").value.trim(),
            description: document.getElementById("description").value.trim(),
          userId: Number(document.getElementById("userId").value)
        };

        try {
            const response = await fetch("/api/disputes", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                alert("Dispute Created Successfully");
                form.reset();
            } else {
                const msg = await response.text();
                alert(msg || "Failed to Create Dispute");
            }

        } catch (error) {
            console.error(error);
            alert("Network/Error Occurred");
        }
    });
});