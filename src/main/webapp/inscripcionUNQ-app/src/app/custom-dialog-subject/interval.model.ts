export class Interval {
  day: string;
  start: Date;
  end: Date;
  constructor(
    day: string,
    start: Date,
    end: Date
  ) {
    this.day = day;
    this.start = start;
    this.end = end;
  }

  public overlapInterval(otherInterval) {
    let overlap = false;

    if (this.day === otherInterval.day) {
        overlap = this.checkOverlap(otherInterval);
    }
    return overlap;
  }

  checkOverlap(otherInterval) {
    return this.timeOverlap(otherInterval) || otherInterval.timeOverlap(this);
  }

  timeOverlap(otherInterval) {
    return (this.start.getTime() >= otherInterval.start.getTime() &&
            this.start.getTime() <= otherInterval.end.getTime()) ||
           (this.end.getTime()   >= otherInterval.start.getTime() &&
            this.end.getTime()   <= otherInterval.end.getTime());
  }
}
