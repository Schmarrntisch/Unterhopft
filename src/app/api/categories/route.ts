import { NextResponse } from "next/server";
import { loadAllCategories } from "@/lib/data-loader";

export async function GET() {
  const categories = loadAllCategories();
  return NextResponse.json(categories);
}
