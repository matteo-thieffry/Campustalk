document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.querySelector('input[name="pseudo_search"]');
    const userItems = document.querySelectorAll('.list-group-item');

    searchInput.addEventListener('input', function () {
        const filter = this.value.toLowerCase();
        userItems.forEach(item => {
            const pseudo = item.querySelector('.fw-bold').textContent.toLowerCase();
            const email = item.querySelector('.text-muted').textContent.toLowerCase();

            if (pseudo.includes(filter) || email.includes(filter)) {
                item.classList.remove('hidden');
            } else {
                item.classList.add('hidden');
            }
        });
    });
});