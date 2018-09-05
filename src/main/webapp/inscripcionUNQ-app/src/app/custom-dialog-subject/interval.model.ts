export class Interval{
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

  overlapInterval(otherInterval){
    let overlap = false;
    
    if(this.day == otherInterval.day){
        overlap = (this.start >= otherInterval.start && this.start <= otherInterval.end)
        || (this.end >= otherInterval.start && this.end <= otherInterval.end);
    }
    return overlap;
  }
}
