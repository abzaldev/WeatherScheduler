export interface IJob {
	className: string;
	jobName: string;
	cityName: string;
	cron: string;
	group: string;
	state: string;
	timezone: string;
}

export type Jobs = IJob[];