import java.util.*;

class Main
{
    private static class CmdsStuff
    {
        String[] num_name;
        String num_format;
        Integer[][] range;
       
        public CmdsStuff(String[] the_num_name, String the_num_format, Integer[][] the_range)
        {
            num_name = the_num_name;
            num_format = the_num_format;
            range = the_range;
        }
    }
    
    private static class Return
    {
        String cmd_init;
        HashMap<String, CmdsStuff> hashmap_cmds;
        ArrayList<CmdsStuff> list_just_nums;
        ArrayList<String> other_cmds;
        int count;
        
        public Return(String the_cmd_init, HashMap<String, CmdsStuff> the_hashmap_cmds
                     ,ArrayList<CmdsStuff> the_list_just_nums, ArrayList<String> the_other_cmds
                     ,int the_count)
        {
            cmd_init = the_cmd_init;
            hashmap_cmds = the_hashmap_cmds;
            list_just_nums = the_list_just_nums;
            other_cmds = the_other_cmds;
            count = the_count;
        }
    }

    private static Integer[] getRangeNum(String input, boolean is_char)
    {
        Integer[] arr = new Integer[2];

        String first_bracket_type = input.substring(0, 1);
        int first_number;
        if (!is_char)
            first_number = Integer.parseInt(input.substring(1, input.indexOf(",")));
        else
            first_number = (int) input.substring(1, input.indexOf(",")).charAt(0);
            
        int second_number; 
        if (!is_char)
            second_number = Integer.parseInt(input.substring(input.indexOf(",")+1, input.length()-1));
        else
            second_number = (int) input.substring(input.indexOf(",")+1, input.length()-1).charAt(0);
        
        String second_bracket_type = input.substring(input.length()-1, input.length());

        if (first_bracket_type.compareTo("(") == 0)
            first_number++;
        if (second_bracket_type.compareTo(")") == 0)
            second_number--;

        arr[0] = first_number;
        arr[1] = second_number;

        return arr;
    }

    private static int generateRandomInRange(Random rand, int min, int max)
    {
        return rand.nextInt(max - min + 1) + min;
    }

    private static void generateTestCase(String cmd_init, HashMap<String, CmdsStuff> hashmap_cmds
                                        ,ArrayList<CmdsStuff> list_just_nums, ArrayList<String> other_cmds
                                        ,int count)
    {
        String first_part = "[";
        String second_part = "";
        if (cmd_init.compareTo("string") != 0)
            second_part += "[";

        Random rand = new Random();

        String temp;
        
        if (cmd_init.compareTo("matrix") == 0)
        {
            for (int i=0; i<count; i++)
            {
                int random_index = generateRandomInRange(rand, 0, list_just_nums.size()-1);

                temp = list_just_nums.get(random_index).num_format;
                for (int j=0; j<list_just_nums.get(random_index).num_name.length; j++)
                {
                    while (temp.contains(list_just_nums.get(random_index).num_name[j]))
                    {
                        int min = list_just_nums.get(random_index).range[j][0];
                        int max = list_just_nums.get(random_index).range[j][1];
                        int random_num = generateRandomInRange(rand, min, max);

                        temp = temp.replaceFirst(list_just_nums.get(random_index).num_name[j], random_num+"");
                    }
                }

                second_part += temp;

                if (i < count-1)
                    second_part += ",";
            }
        }
        else if (cmd_init.compareTo("string") == 0)
        {
            for (int i=0; i<count; i++)
            {
                int random_index = generateRandomInRange(rand, 0, list_just_nums.size()-1);

                temp = list_just_nums.get(random_index).num_format;
                
                for (int j=0; j<list_just_nums.get(random_index).num_name.length; j++)
                {
                    while (temp.contains(list_just_nums.get(random_index).num_name[j]))
                    {
                        int random_other_index = generateRandomInRange(rand, 0, list_just_nums.get(random_index).range.length-1);
                        
                        int min = list_just_nums.get(random_index).range[random_other_index][0];
                        int max = list_just_nums.get(random_index).range[random_other_index][1];
                        
                        int random_num = generateRandomInRange(rand, min, max);

                        temp = temp.replaceFirst(list_just_nums.get(random_index).num_name[j], ((char) random_num)+"");
                    }
                }

                second_part += temp;
            }
        }
        else if (cmd_init.compareTo("") != 0)
        {
            first_part += cmd_init;

            temp = hashmap_cmds.get(cmd_init).num_format;
            for (int j=0; j<hashmap_cmds.get(cmd_init).num_name.length; j++)
            {
                int min = hashmap_cmds.get(cmd_init).range[j][0];
                int max = hashmap_cmds.get(cmd_init).range[j][1];
                int random_num = generateRandomInRange(rand, min, max);

                temp = temp.replace(hashmap_cmds.get(cmd_init).num_name[j], random_num+"");
            }

            second_part += temp;

            for (int i=0; i<count; i++)
            {
                int random_index = generateRandomInRange(rand, 0, other_cmds.size()-1);

                first_part += ","+other_cmds.get(random_index);

                temp = hashmap_cmds.get(other_cmds.get(random_index)).num_format;
                for (int j=0; j<hashmap_cmds.get(other_cmds.get(random_index)).num_name.length; j++)
                {
                    int min = hashmap_cmds.get(other_cmds.get(random_index)).range[j][0];
                    int max = hashmap_cmds.get(other_cmds.get(random_index)).range[j][1];
                    int random_num = generateRandomInRange(rand, min, max);

                    temp = temp.replace(hashmap_cmds.get(other_cmds.get(random_index)).num_name[j], random_num+"");
                }

                second_part += ","+temp;
            }

            first_part += "]";
            System.out.println(first_part);
        }
        else
        {
            for (int i=0; i<count; i++)
            {
                int random_index = generateRandomInRange(rand, 0, list_just_nums.size()-1);

                temp = list_just_nums.get(random_index).num_format;
                for (int j=0; j<list_just_nums.get(random_index).num_name.length; j++)
                {
                    while (temp.contains(list_just_nums.get(random_index).num_name[j]))
                    {
                        int min = list_just_nums.get(random_index).range[j][0];
                        int max = list_just_nums.get(random_index).range[j][1];
                        int random_num = generateRandomInRange(rand, min, max);

                        temp = temp.replaceFirst(list_just_nums.get(random_index).num_name[j], random_num+"");
                    }
                }

                second_part += temp;

                if (i < count-1)
                    second_part += ",";
            }
        }

        if (cmd_init.compareTo("string") != 0)
            second_part += "]";
        System.out.println(second_part);
    }
    
    private static Return getInput(Scanner scanner)
    {
        System.out.println("Input one of the following:");
        System.out.println("    \"command\" if the testcase uses commands like \'get\' or \'set\'");
        System.out.println("    \"matrix\" if the testcase uses a matrix");
        System.out.println("    \"array\" if the testcase uses an array");
        System.out.println("    \"string\" if the testcase uses a string");
        String temp = scanner.nextLine();

        HashMap<String, CmdsStuff> hashmap_cmds = new HashMap<>();
        ArrayList<CmdsStuff> list_just_nums = new ArrayList<>();

        String cmd_init = "";
        ArrayList<String> other_cmds = new ArrayList<>();
        
        int count = -1;

        if (temp.compareTo("matrix") == 0 || temp.compareTo("array") == 0)
        {
            cmd_init = "matrix";
            String other_cmd_init = temp.compareTo("matrix") == 0 ? "matrix" : "array";
            
            if (other_cmd_init.compareTo("matrix") == 0)
            {
                System.out.println("Input the size of the matrix (ex: \"20x100\").");
                temp = scanner.nextLine();
                
                count = Integer.parseInt(temp.substring(temp.indexOf("x")+1, temp.length()));
            }
            else
            {
                System.out.println("Input the length of the array (ex: \"100\").");
                temp = scanner.nextLine();
                
                count = Integer.parseInt(temp);
            }
            
            String[] cmd_nums = {"index"};
            String cmd_num_format = "";
            int row_size;
            if (other_cmd_init.compareTo("matrix") == 0)
            {
                cmd_num_format = "[";
                row_size = Integer.parseInt(temp.substring(0, temp.indexOf("x")));
            }
            else
                row_size = 1;
                
            for (int i=0; i<row_size; i++)
            {
                cmd_num_format += "index";
                if (i < row_size-1)
                    cmd_num_format += ",";
            }
            if (other_cmd_init.compareTo("matrix") == 0)
                cmd_num_format += "]";
            
            System.out.println("Input range of values at each index (ex: \"(0,100]\").");
            temp = scanner.nextLine();
            
            Integer[][] list_ranges = new Integer[1][2];
            list_ranges[0] = getRangeNum(temp, false);
            
            list_just_nums.add(new CmdsStuff(cmd_nums, cmd_num_format, list_ranges));
        }
        else if (temp.compareTo("string") == 0)
        {
            cmd_init = "string";
            
            String[] cmd_nums = {"char"};
            String cmd_num_format = "char";
            
            System.out.println("Input range of values for each character (ex: \"(a,z]\"). Input multiple ranges like so (ex: \"(a,z] , [A,Z] , [0,9)\"");
            temp = scanner.nextLine();
            
            String[] ranges = temp.split(" , ");
            
            Integer[][] list_ranges = new Integer[ranges.length][2];
            for (int i=0; i<ranges.length; i++)
                list_ranges[i] = getRangeNum(ranges[i], true);
            
            list_just_nums.add(new CmdsStuff(cmd_nums, cmd_num_format, list_ranges));
        }
        else if (temp.compareTo("command") == 0)
        {
            System.out.println("Input an initializing command (ex: \"LRUCache:[capacity]\").");
            temp = scanner.nextLine();
            
            cmd_init = "\""+temp.substring(0, temp.indexOf(":"))+"\"";

            String[] cmd_init_nums = temp.substring(temp.indexOf(":")+2, temp.length()-1).split(",");
            String cmd_init_num_format = temp.substring(temp.indexOf(":")+1, temp.length());

            Integer[][] list_ranges = new Integer[cmd_init_nums.length][2];

            for (int i=0; i<cmd_init_nums.length; i++)
            {
                System.out.println("Enter range of "+cmd_init_nums[i]+" (ex: \"(0,100]\")");
                temp = scanner.nextLine();
                list_ranges[i] = getRangeNum(temp, false);
            }

            hashmap_cmds.put(cmd_init, new CmdsStuff(cmd_init_nums, cmd_init_num_format, list_ranges));

            while (true)
            {
                System.out.println("\nInput other commands (ex: \"get:[key]\"). Hit enter when done.");

                temp = scanner.nextLine();
                if (temp.compareTo("") == 0)
                    break;

                String cmd = "\""+temp.substring(0, temp.indexOf(":"))+"\"";

                String[] cmd_nums = temp.substring(temp.indexOf(":")+2, temp.length()-1).split(",");
                String cmd_num_format = temp.substring(temp.indexOf(":")+1, temp.length());

                list_ranges = new Integer[cmd_nums.length][2];

                for (int i=0; i<cmd_nums.length; i++)
                {
                    System.out.println("Enter range of "+cmd_nums[i]+" (ex: \"(0,100]\")");
                    temp = scanner.nextLine();
                    list_ranges[i] = getRangeNum(temp, false);
                }

                hashmap_cmds.put(cmd, new CmdsStuff(cmd_nums, cmd_num_format, list_ranges));
                other_cmds.add(cmd);
            }
        }
        else 
        {
            while (true)
            {
                System.out.println("\nInput format of objects in array, using letters in place of numbers (ex: \"[a,a]\"). Hit enter when done.");

                temp = scanner.nextLine();
                if (temp.compareTo("") == 0)
                    break;

                String[] cmd_nums = temp.substring(temp.indexOf(":")+2, temp.length()-1).split(",");
                String cmd_num_format = temp.substring(temp.indexOf(":")+1, temp.length());

                Integer[][] list_ranges = new Integer[cmd_nums.length][2];

                for (int i=0; i<cmd_nums.length; i++)
                {
                    System.out.println("Enter range of "+cmd_nums[i]+" (ex: \"(0,100]\")");
                    temp = scanner.nextLine();
                    list_ranges[i] = getRangeNum(temp, false);
                }

                list_just_nums.add(new CmdsStuff(cmd_nums, cmd_num_format, list_ranges));
            }
        }

        String sorted;
        
        if (count == -1)
        {
            System.out.println("Input how many to generate (ex: 1000).");
            count = Integer.parseInt(scanner.nextLine());
            
            if (cmd_init.compareTo("") == 0)
            {
                System.out.println("If it needs to be sorted, type \"ASC\" or \"DESC\". Otherwise, hit enter.");
                sorted = scanner.nextLine();
            }
        }
        
        return new Return(cmd_init, hashmap_cmds, list_just_nums, other_cmds, count);
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        Return the_return = getInput(scanner);
        
        String cmd_init = the_return.cmd_init;
        HashMap<String, CmdsStuff> hashmap_cmds = the_return.hashmap_cmds;
        ArrayList<CmdsStuff> list_just_nums = the_return.list_just_nums;
        ArrayList<String> other_cmds = the_return.other_cmds;
        int count = the_return.count;

        generateTestCase(cmd_init, hashmap_cmds, list_just_nums, other_cmds, count);

        while (true)
        {
            System.out.print("Hit enter to regenerate, or \"restart\" to restart this process");
            if (cmd_init.compareTo("matrix") != 0)
                System.out.println(", or enter another amount (ex: 1000).");
            else
                System.out.println();
                
            String maybe_count = scanner.nextLine();
            if (maybe_count.compareTo("") != 0)
            {
                if (maybe_count.compareTo("restart") == 0)
                {
                    the_return = getInput(scanner);
                    
                    cmd_init = the_return.cmd_init;
                    hashmap_cmds = the_return.hashmap_cmds;
                    list_just_nums = the_return.list_just_nums;
                    other_cmds = the_return.other_cmds;
                    count = the_return.count;
                }
                else
                    count = Integer.parseInt(maybe_count);
            }
            
            System.out.println();
            generateTestCase(cmd_init, hashmap_cmds, list_just_nums, other_cmds, count);
        }
    }
}
