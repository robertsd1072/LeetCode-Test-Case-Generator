import java.util.*;

class LeetCodeTestCaseGenerator
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

    private static Integer[] getRangeNum(String input)
    {
        Integer[] arr = new Integer[2];

        String first_bracket_type = input.substring(0, 1);
        int first_number = Integer.parseInt(input.substring(1, input.indexOf(",")));
        int second_number = Integer.parseInt(input.substring(input.indexOf(",")+1, input.length()-1));
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
        String second_part = "[";

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

        second_part += "]";
        System.out.println(second_part);
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input init command (ex: \"LRUCache:[capacity]\") or hit enter if none. If you wish a matrix to be generated, type \"matrix\".");
        String temp = scanner.nextLine();

        HashMap<String, CmdsStuff> hashmap_cmds = new HashMap<>();
        ArrayList<CmdsStuff> list_just_nums = new ArrayList<>();

        String cmd_init = "";
        ArrayList<String> other_cmds = new ArrayList<>();
        
        int count = -1;

        if (temp.compareTo("matrix") == 0)
        {
            cmd_init = "matrix";
            
            System.out.println("Input the size of the matrix (ex: \"20x100\").");
            temp = scanner.nextLine();
            
            count = Integer.parseInt(temp.substring(temp.indexOf("x")+1, temp.length()));
            
            String[] cmd_nums = {"index"};
            String cmd_num_format = "[";
            int row_size = Integer.parseInt(temp.substring(0, temp.indexOf("x")));
            for (int i=0; i<row_size; i++)
            {
                cmd_num_format += "index";
                if (i < row_size-1)
                    cmd_num_format += ",";
            }
            cmd_num_format += "]";
            
            System.out.println("Input range of values at each index (ex: \"(0,100]\").");
            temp = scanner.nextLine();
            
            Integer[][] list_ranges = new Integer[1][2];
            list_ranges[0] = getRangeNum(temp);
            
            list_just_nums.add(new CmdsStuff(cmd_nums, cmd_num_format, list_ranges));
        }
        else if (temp.compareTo("") != 0)
        {
            cmd_init = "\""+temp.substring(0, temp.indexOf(":"))+"\"";

            String[] cmd_init_nums = temp.substring(temp.indexOf(":")+2, temp.length()-1).split(",");
            String cmd_init_num_format = temp.substring(temp.indexOf(":")+1, temp.length());

            Integer[][] list_ranges = new Integer[cmd_init_nums.length][2];

            for (int i=0; i<cmd_init_nums.length; i++)
            {
                System.out.println("Enter range of "+cmd_init_nums[i]+" (ex: \"(0,100]\")");
                temp = scanner.nextLine();
                list_ranges[i] = getRangeNum(temp);
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
                    list_ranges[i] = getRangeNum(temp);
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
                    list_ranges[i] = getRangeNum(temp);
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

        generateTestCase(cmd_init, hashmap_cmds, list_just_nums, other_cmds, count);

        while (true)
        {
            System.out.print("Hit enter to regenerate");
            if (cmd_init.compareTo("matrix") != 0)
                System.out.println(", or enter another amount (ex: 1000).");
            else
                System.out.println();
                
            String maybe_count = scanner.nextLine();
            if (maybe_count.compareTo("") != 0)
                count = Integer.parseInt(maybe_count);

            System.out.println();
            generateTestCase(cmd_init, hashmap_cmds, list_just_nums, other_cmds, count);
        }
    }
}
