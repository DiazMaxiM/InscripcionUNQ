export class Poll {
  id: number;
  startDate: Date;
  endDate: Date;
  name: string;
  constructor(
    id: number,
    startDate: Date,
    endDate: Date,
    name: string
  ) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;

  }

  }
