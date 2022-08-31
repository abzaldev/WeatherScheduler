
export const api = {

	async get<T>(url: string, params?: any): Promise<T> {
		try {
			let realUrl = url;
			if (params) {
				realUrl = `${url}?${new URLSearchParams(params).toString()}`;
			}
			const request = await fetch(realUrl, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'X-Requested-With': 'XMLHttpRequest'
				}
			});
			if (request.ok) {
				const response = await request.json();
				return response as T;
			} else {
				console.error(request);
				if (request.status === 401) {
					window.location.href = '401';
				}
			}
			throw new Error(request.statusText);
		} catch (err) {
			console.log(err);
			throw err;
		}
	},

	async post<T>(url: string, data?: any): Promise<T> {
		const request = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-Requested-With': 'XMLHttpRequest'
			},
			body: typeof data === 'string' ? data : JSON.stringify(data)
		});
		if (request.ok) {
			const response = await request.json();
			return response as T;
		} else {
			if (request.status === 401) {
				window.location.href = '401';
			}
		}
		throw new Error(request.statusText);
	},

	async put<T>(url: string, data?: any): Promise<T> {
		const request = await fetch(url, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				'X-Requested-With': 'XMLHttpRequest'
			},
			body: typeof data === 'string' ? data : JSON.stringify(data)
		});
		if (request.ok) {
			const response = await request.json();
			return response as T;
		} else {
			if (request.status === 401) {
				window.location.href = '401';
			}
		}
		throw new Error(request.statusText);
	},

	async delete<T>(url: string, data?: any): Promise<T> {
		const request = await fetch(url, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json',
				'X-Requested-With': 'XMLHttpRequest'
			},
			body: typeof data === 'string' ? data : JSON.stringify(data)
		});
		if (request.ok) {
			const response = await request.json();
			return response as T;
		} else {
			if (request.status === 401) {
				window.location.href = '401';
			}
		}
		throw new Error(request.statusText);
	},


};
